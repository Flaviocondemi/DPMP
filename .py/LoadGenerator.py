from prometheus_api_client import PrometheusConnect
import pymongo
import requests
from random import randint
import random
from datetime import datetime
import json
import matplotlib.pyplot as plt

'''
 ____                _ __  __      
|  _ \ ___  __ _  __| |  \/  | ___ 
| |_) / _ \/ _` |/ _` | |\/| |/ _ \
|  _ <  __/ (_| | (_| | |  | |  __/
|_| \_\___|\__,_|\__,_|_|  |_|\___|

This is not only a python load balancer!
Goal of this script:
 1. Make get and post request to spring-boot endpoints
 2. Scrape date from prometheus: in this case we are scraping reasponse-time metrics
 3. Save metrics scraped from prometheus to mongoDB
'''

'''
  ____             __ _        __     __         _       _     _      
 / ___|___  _ __  / _(_) __ _  \ \   / /_ _ _ __(_) __ _| |__ | | ___ 
| |   / _ \| '_ \| |_| |/ _` |  \ \ / / _` | '__| |/ _` | '_ \| |/ _ \
| |__| (_) | | | |  _| | (_| |   \ V / (_| | |  | | (_| | |_) | |  __/
 \____\___/|_| |_|_| |_|\__, |    \_/ \__,_|_|  |_|\__,_|_.__/|_|\___|
                        |___/    
'''

URL_SPRING_APP = 'http://localhost:8080'
AMOUNT_OF_REQUESTS = 2000
CPU_USAGE= 1
MEMORY_USAGE = 100

# MongoDB connection
client = pymongo.MongoClient("mongodb://localhost:27017/")
ResponseTimeMetricsDB = client["ResponseTimeMetrics"]
metricsGetPeople = ResponseTimeMetricsDB["GetPeopleMetrics"]
metricsGetPeopleByLocation = ResponseTimeMetricsDB["PeopleByLocationMetrics"]


# Prometheus endpoint
prom = PrometheusConnect(url="http://localhost:9090/", disable_ssl=True)


def getPeople_request(amount):
    # getPeople Endpoint
    requests.get(URL_SPRING_APP + '/get?amount=' + str(amount))

def post_request():
    url_api = "http://localhost:8080/post"
    getReq = requests.get('https://randomuser.me/api/')

    if getReq.status_code == 200:
        jsonData = getReq.json()

        print(jsonData)  # Print for debug

        # Person
        headers = {'Content-Type': 'application/json'}
        name = jsonData["results"][0]["name"]["first"]
        surname = jsonData["results"][0]["name"]["last"]
        gender = jsonData["results"][0]["gender"]
        age = jsonData["results"][0]["dob"]["age"]

        # Location
        location = jsonData["results"][0]["location"]["street"]["name"]
        city = jsonData["results"][0]["location"]["city"]
        country = jsonData["results"][0]["location"]["country"]
        postcode = jsonData["results"][0]["location"]["postcode"]
        state = jsonData["results"][0]["location"]["state"]

        location = {
            "location": location,
            "city": city,
            "country": country,
            "postcode": postcode,
            "state": state
        }

        # Credentials
        username = jsonData["results"][0]["login"]["username"]
        password = jsonData["results"][0]["login"]["password"]
        mail = jsonData["results"][0]["email"]

        credentials = {
            "username": username,
            "password": password,
            "mail": mail
        }

        id = i

        person = {
            "id": id,
            "age": age,
            "gender": gender,
            "location": location,
            "mail": mail,
            "name": name,
            "surname": surname,
            "credentials": credentials
        }

        print(person)
        res = json.dumps(person)

        postReq = requests.post(url_api, data=res, headers=headers)


'''
   --------------------------------------------------
    SCRIPT POST AND GET GENERATOR 
   --------------------------------------------------
'''

i = 0

''' -------  REQUEST LOOP TO ALL SPRING APP ENDPOINT --------'''

while i < AMOUNT_OF_REQUESTS:
    random_amount_of_people = randint(0, 2000)
    getPeople_request(random_amount_of_people)
    # TODO: Add post request to add People in mysql  ....
    ''' ENDPOINT -> GetPeople  '''
    # Promethues query to scrape date of getPeople endpoint
    rp_getPeople = prom.custom_query(query="response_time_getPeople")
    response_time_value_getPeople = rp_getPeople[0]['value'][1]
    # Inserting response time and timestamp in mongo document
    metricsGetPeople.insert_one({'response-time': response_time_value_getPeople,
                                 'time-stamp': datetime.utcnow(),
                                 'time-stamp': datetime.utcnow(),
                                 'cpu-usage': CPU_USAGE,
                                 'memory-usage': MEMORY_USAGE,
                                 'amount-people': random_amount_of_people,
                                 })

    # TODO: add other get request to spring app endpoints ....
    i = i + 1

endTime = datetime.utcnow()

''' ------ PRINT ALL ENDPOINT METRICS COLLECTED IN MONGO -------'''
print("Response GetPeople Endpoint response-time metrics from MONGO: ")
for people in metricsGetPeople.find():
    print("Response time: " + str(people["response-time"]))
    print("People: " + str(people["amount-people"]))
print("Response GetAllPeopleLivingInASpecificPlace Endpoint response-time metrics from MONGO: ")
for peopleByLocation in metricsGetPeopleByLocation.find():
    print(peopleByLocation)

