from asyncio import gather
from typing import Dict
from sanic import Sanic
from sanic.response import json

from app.providers import wikipedia, adm, base

app = Sanic("pyrino")

@app.before_server_start
async def main_start(_):
    providers: Dict[str, base.BaseProvider] = {
        "adm": adm.Provider(),
        "wiki": wikipedia.Provider()
    }
    app.ctx.providers = providers
    app.ctx.default_providers = [p for p in providers.values() if p.enabled_by_default() ]

SUGGEST_RESPONSE = {
  "suggestions": [],
  "client_variants": [],
  "server_variants": [],
  "request_id": "2fd3d126-dc91-4a9d-8aad-9b4bb7d85ed9"
}

@app.route("/search")
async def search(request):
    query = request.args.get("q")
    if "providers" in request.args:
        providers = [app.ctx.providers[p] for p in request.args.get("providers").split(",") if p in app.ctx.providers]
    else:
        providers = app.ctx.default_providers
    lookups = [p.query(query) for p in providers]
    results = await gather(*lookups)
    if len(results):
        SUGGEST_RESPONSE["suggestions"] = [sugg for provider_results in results for sugg in provider_results]
    return json(SUGGEST_RESPONSE)

@app.route("/providers")
async def providers(request):
    response = []
    for id, provider in app.ctx.providers.items():
        response.append({
                            "id": id,
                            "availability": provider.availability()
                        })
    return json(response)
