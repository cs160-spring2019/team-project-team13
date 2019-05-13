# Pet Plant (Team 13)
## Description
Pet Plant currently supports the ability to scan for or manually retreive plant information, scan for plant disease information,
set reminders in an in-app calendar for watering plants, and in-app communication with plant experts. We are currently using our own API for disease information retreival, using [Plant.id](https://plant.id/api) for identifying plants, and Wikipedia API for plant information for further information about the plant that was identified. In the backend for the in-app communication with plant experts, we are using Firebase for support.

## Build and Run
Best run on Android Studio 3.4.

1. After cloning go into the PetPlant directory and Import Project into Android Studio using all of the default settings for the import.
2. If no configurations are made, go to Edit Configurations, click the add button, and name a new build/run configuration with the default settings and the Module set as the app directory.
3. Then go to ``Build`` and select ``Make Project`` to build the project.
4. After go to ``Run`` and then select ``Run App`` to run the application on the android emulator or on a native device. (NOTE: To properly use the APIs we've implemented into our app, it is best to run our app on a native device to take pictures of actual plants or fruit for plant identification and plant disease identification)

NOTE: If there are issues regarding using our camera, go to the Diagnosis tab and make sure the camera is allowed on your device first. Then you should be able to use the camera on both the Diagnosis of plant diseases and plant identification.

## Deploying Django Server
The scanning disease function of this application is depended on a convolutional neural network based API. The API is implemented with Tensorflow and deployed with Django as a web service.

### Before June 15th, 2019
The service is currently deployed in Google Cloud which can accept public HTTP request at http://35.247.41.9/predict. 
It means that before June 1st you don't need to worry about the API, the application will run properly by itself.

### After June 15th, 2019
Since the service costs $24.67 per month, I am very likely to cut down the service after June 15th. Below are the steps you need to test the application on your localhost. But before that 
<strong>note that the localhost can only be accessed from your own computer, which means you could only test the function on your emulator, but not a real Android device</strong>.

1. Clone the alexnet_API folder
2. Download the model file from <https://drive.google.com/file/d/1Avvlkf1xcp_1ABMLsuR3Z9hcS14yWWez/view?usp=sharing>, and put it in ``alexnet_API/plant_disease_detector/``
3. Install Python3 (It is highly recommended to use ``virtualenv`` to set up a virtual environment.)
4. `cd` to the ``alexnet_API``, install all dependency with ``pip install -r requirements.txt`` (use pip3 if it does not work.)
5. Run the server with command line ``python3 manage.py runserver``. By default, the service will start at your ``localhost``, usually ``127.0.0.1``.
6. The local host IP address on the emulator is very weird (details: <https://stackoverflow.com/questions/5806220/how-to-connect-to-my-http-localhost-web-server-from-android-emulator-in-eclips>)
In order to run, you have to change the URL in ``PetPlant/app/src/main/java/com/example/petplant/camera/view/DiseaseActivity.java`` line 46 to ``http://10.0.2.2:8000/``

Now, enjoy the API (:

## Authors and Acknowledgement
Pet Plant was designed and implemented by Jacob Tran, Lichen Wang, Zivon Yang, Kyle Dunne, and Rena Chen. 

Special thanks to Professor Bjoern Hartmann, Elva Chen, and the rest of the CS160 Spring 2019 course staff for a fun and fulfilling semester!
