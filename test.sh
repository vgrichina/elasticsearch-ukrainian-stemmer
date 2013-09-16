# Remove old data
curl -XDELETE "http://localhost:9200/ukrainian"

# Create index with settings
curl -XPOST "http://localhost:9200/ukrainian/" -d '
{
   "settings":{
      "index":{
         "analysis":{
            "analyzer":{
               "default":{
                  "type":"ukrainian"
               },
               "ukrainian":{
                  "type":"ukrainian"
               }
            }
         }
      }
   }
}
'

# Define mapping
curl -XPOST "http://localhost:9200/ukrainian/user/_mapping" -d '
{
   "user":{
      "_all":{
         "analyzer":"ukrainian"
      },
      "properties":{
         "test":{
            "type":"string",
            "analyzer":"ukrainian"
         }
      }
   }
}
'

# Create Document
curl -XPOST "http://localhost:9200/ukrainian/user/" -d '
{
   "test":"Історії"
}
'

# Wait for ES to be synced (aka refresh indices)
curl -XPOST "http://localhost:9200/ukrainian/_refresh"

# Search
curl -XPOST "http://localhost:9200/ukrainian/user/_search?pretty=true" -d '
{
   "query":{
      "match":{
         "_all": {
             "query": "історія",
             "analyzer": "ukrainian"
         }
      }
   }
}
'
