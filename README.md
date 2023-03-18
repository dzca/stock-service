# stock-service
springboot service for upload stocks

## Assumption
- record in uploaded file come from single source, e.g. duplicated records are same, overwrite is safe.
- multiple user can upload file in same time, but file contents might have overlay.
- uploaded data a stored in one table, all query are searching on the same table.
- the file upload limit is 1 kb.
- total data size in any time does not exceed 4GB

## API
1. upload file [POST] /stocks/upload
2. query by ticker [GET] /stocks/ticker/{ticker}
3. add a new stock [POST] /stocks/

sample stock json
```
{
    "quarter": 1,
    "ticker": "XYZ",
    "date": "3/18/2023",
    "open": "$56.19",
    "close": "$55.79",
    "volume": 138428495,
    "percentChangePrice": "-2.47066",
    "percentChangeVolumeLastWeek": -43.02495926
}
```

## Implementation detail
1. file uploaded into a disk, different user have their own account name.
2. uploaded file a saved in a data folder
3. in every 5 sec, a background process will load a parser to read the file into redis.
4. the data file will be deleted after read
5. if user uploaded a bad file that cannot process, it will be stored in a folder for auditing
6. the requests will invalid token will be rejected(HTTP 401)

## Error codes

- 50001, DATABASE_ERROR, (500)
- 50002, CACHE_ERROR, (500)
- 40003, INVALID_ACCOUNT, (400)
- 40004, FILE_SIZE_EXCEEDS, (400)
- 40005, INVALID_TOKEN, (401)

