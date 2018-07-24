import sys, time
import cv2
import numpy as np

def extract_image_one_fps(video_source_path, v, n_sec, step):
    sec = 0
    while sec < n_sec:
      v.set(cv2.CAP_PROP_POS_MSEC, sec)      
      success,image = v.read()

      if not success:
        raise ValueError("retrieve frame error")

      #cv2.imwrite("frame%d.png" % sec, image)     # save frame as PNG file
      #print('{}.sec reading a new frame: {} '.format(sec, success))
      sec += step


video_source_path = sys.argv[1]

v = cv2.VideoCapture(video_source_path)
v.set(cv2.CAP_PROP_POS_AVI_RATIO, 1)
n_sec = v.get(cv2.CAP_PROP_POS_MSEC)

rates = [1, 1, 2, 3, 4, 5]
for rate in rates:
  tick = time.time()
  extract_image_one_fps(video_source_path, v, n_sec, 1000 / rate)
  tock = time.time()
  print(n_sec * 1.0 / (tock - tick))