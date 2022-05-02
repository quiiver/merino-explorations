from asyncio import gather
from sanic import Sanic
from sanic.response import json
from .providers import adm

app = Sanic("pyrino")

@app.before_server_start
async def main_start(_):
    app.ctx.providers = [adm.AdmProvider()]

RESPONSE = {
  "suggestions": [],
  "client_variants": [],
  "server_variants": [],
  "request_id": "2fd3d126-dc91-4a9d-8aad-9b4bb7d85ed9"
}

@app.route("/search")
async def search(request):
    query = request.args.get("q")
    lookups = [provider.query(query) for provider in request.ctx.providers]
    results = await gather(*lookups)
    if len(results):
        RESPONSE["suggestions"] = [sugg for provider_results in results for sugg in provider_results]
    return json(RESPONSE)
