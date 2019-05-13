import datetime
import pickle
import json
import numpy as np
from django.shortcuts import render
from django.http import HttpResponse
from rest_framework.decorators import api_view
from api.settings import BASE_DIR
from .deeplearning import graph, model, output_list

from custom_code import image_converter

@api_view(['GET'])
def __index__function(request):
    start_time = datetime.datetime.now()
    elapsed_time = datetime.datetime.now() - start_time
    elapsed_time_ms = (elapsed_time.days * 86400000) + (elapsed_time.seconds * 1000) + (elapsed_time.microseconds / 1000)
    return_data = {
        "error" : "0",
        "message" : "Successful",
        "restime" : elapsed_time_ms
    }
    return HttpResponse(json.dumps(return_data), content_type='application/json; charset=utf-8')

@api_view(['POST','GET'])
def predict_plant_disease(request):
    try:
        if request.method == "GET" :
            return_data = {
                "error" : "0",
                "message" : "Plant Disease Recognition API. Built by Lichen Wang @Team 13 CS260"
            }
        else:
            if request.body:
                request_data = request.data["plant_image"]
                request_data += "=" * ((4 - len(request_data) % 4) % 4)
                image_array, err_msg = image_converter.convert_image(request_data)
                print (err_msg)
                if err_msg == None :

                    with graph.as_default():
                        prediction = model.predict(image_array)

                    prediction_flatten = prediction.flatten()
                    max_val_index = np.argmax(prediction_flatten)
                    result = output_list[max_val_index]
                    prob = max(prediction_flatten)*100
                    # return Response({'result': result})


                    # model_file = f"{BASE_DIR}/ml_files/cnn_model.pkl"
                    # saved_classifier_model = pickle.load(open(model_file,'rb'))
                    # prediction = saved_classifier_model.predict(image_array) 
                    # label_binarizer = pickle.load(open(f"{BASE_DIR}/ml_files/label_transform.pkl",'rb'))

                    return_data = {
                        "prob" : '%.2f%%' % prob,
                        "data" : result
                    }
                else :
                    return_data = {
                        "error" : "4",
                        "message" : f"Error : {err_msg}"
                    }
            else :
                return_data = {
                    "error" : "1",
                    "message" : "Request Body is empty",
                }
    except Exception as e:
        return_data = {
            "error" : "3",
            "message" : f"Error : {str(e)}",
        }
    return HttpResponse(json.dumps(return_data), content_type='application/json; charset=utf-8')