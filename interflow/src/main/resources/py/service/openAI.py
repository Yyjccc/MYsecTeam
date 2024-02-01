import httpx
import requests
import yaml


from openai import OpenAI





with open("../../application.yml", "r") as f:
    config = yaml.safe_load(f)


key=config['ai']['key']['openai']

key='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'
client = OpenAI(
  organization='org-Do9WLevSwNcE3P4I4K8T2f2J',
    api_key=key,
    http_client=httpx.Client(
        proxies="http://127.0.0.1:8090",
        transport=httpx.HTTPTransport(local_address="0.0.0.0")
)
)



stream = client.chat.completions.create(
    model="gpt-3.5-turbo",
    messages=[{"role": "user", "content": "Say this is a test"}],
    stream=True,
)
print()
for chunk in stream:
    if chunk.choices[0].delta.content is not None:
        print(chunk.choices[0].delta.content, end="")




