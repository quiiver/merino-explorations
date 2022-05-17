from asyncio import gather
from sanic import Sanic
from sanic.response import json

from app.providers import wikipedia
from .providers import adm

app = Sanic("pyrino")

@app.before_server_start
async def main_start(_):
    app.ctx.providers = {
        "adm": adm.Provider(),
        "wiki": wikipedia.Provider()
    }

RESPONSE = {
  "suggestions": [],
  "client_variants": [],
  "server_variants": [],
  "request_id": "2fd3d126-dc91-4a9d-8aad-9b4bb7d85ed9"
}

@app.route("/search")
async def search(request):
    query = request.args.get("q")
    providers = request.args.get("p", "adm").split(",")
    lookups = [app.ctx.providers[p].query(query) for p in providers]
    results = await gather(*lookups)
    if len(results):
        RESPONSE["suggestions"] = [sugg for provider_results in results for sugg in provider_results]
    return json(RESPONSE)
