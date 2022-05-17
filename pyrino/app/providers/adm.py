import traceback
from typing import List, Dict, Any

from app import remotesettings

class Provider():
    
    suggestions: Dict[str, int] = {}
    results: List[Dict[str, Any]] = []
    icons: Dict[int, str] = {}

    def __init__(self):
        rs = remotesettings.Client()
        suggest_settings = rs.get("main", "quicksuggest")
        data_items = [i for i in suggest_settings if i['type'] == 'data']
        for item in data_items: 
            res = rs.fetch_attachment(item['attachment']['location'])
            for suggestion in res.json():
                id = len(self.results)
                for kw in suggestion.get("keywords"):
                    self.suggestions[kw] = id
                self.results.append({k:suggestion[k] for k in suggestion if k != 'keywords'})
        icon_items = [i for i in suggest_settings if i['type'] == 'icon']
        for icon in icon_items:
            self.icons[icon["id"]] = icon["attachment"]["location"]

    async def query(self, q: str):
        id = self.suggestions.get(q)
        if id != None:
            res = self.results[id]
            if res != None:
                return [{
                    "block_id": res.get("id"),
                    "full_keyword": q,
                    "title": res.get("title"),
                    "url": res.get("url"),
                    "impression_url": res.get("impression_url"),
                    "click_url": res.get("click_url"),
                    "provider": "adm",
                    "advertiser": res.get("advertiser"),
                    "is_sponsored": True,
                    "icon": self.icons.get(res.get("icon")),
                    "score": 0.5,
                }]
        return []
