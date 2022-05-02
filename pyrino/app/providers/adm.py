import os
from json import load
from typing import Optional, Dict, Any

SUGGEST_FILES_PATH = os.path.join(os.path.dirname(os.path.realpath(__file__)), "../../data/")

class AdmProvider():
    
    suggestions: Optional[Dict[str, int]] = None
    results: Optional[Dict[int, Dict[str, Any]]] = None

    def __init__(self):
        if self.suggestions is None:
            with open(os.path.join(SUGGEST_FILES_PATH, "InstantSuggest_Queries_20220125.json")) as queries_file:
                self.suggestions = load(queries_file).get("mapping")
        if self.results is None:
            self.results = {}
            with open(os.path.join(SUGGEST_FILES_PATH, "InstantSuggest_Results_20220125.json")) as queries_file:
                blocks = load(queries_file).get("blocks")
                for block in blocks:
                    self.results[block.get("id")] = block

    async def query(self, q: str):
        if self.suggestions is not None:
            id = self.suggestions.get(q)
            if id != None and self.results is not None:
                res = self.results.get(id)
                if res != None:
                    brand = res["brand"]
                    return [{
                        "block_id": brand.get("id"),
                        "full_keyword": q,
                        "title": brand.get("title"),
                        "url": brand.get("advertiser_url"),
                        "impression_url": res.get("impression_url"),
                        "click_url": brand.get("click_url"),
                        "provider": "adm",
                        "advertiser": brand.get("name"),
                        "is_sponsored": True,
                        "icon": brand.get("image_url"),
                        "score": 0.5,
                    }]
        return []
