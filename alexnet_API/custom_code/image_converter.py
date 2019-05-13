import io
import base64
import numpy as np
from PIL import Image
from keras.preprocessing.image import img_to_array

default_image_size = tuple((224, 224))

def convert_image(image_data):
    try:
        #image = Image.open(image_data)
        #image_data += "=" * ((4 - len(image_data) % 4) % 4)
        image = Image.open(io.BytesIO(base64.b64decode(image_data)))
        if image is not None :
            image = image.resize(default_image_size)   
            image_array = img_to_array(image)
            return np.expand_dims(image_array, axis=0)/255, None
        else :
            return None, "Error loading image file"
    except Exception as e:
        return None, str(e)