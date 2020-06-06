import math

import numpy as np
import matplotlib.pyplot as plt
from scipy.misc import imread
from scipy.spatial import distance



# data preperation (loading, normalizing, reshaping)
path = 'dog.jpeg'
A = imread(path)
A_norm = A.astype(float) / 255.
img_size = A_norm.shape
X = A_norm.reshape(img_size[0] * img_size[1], img_size[2])

import numpy as np


def init_centroids(X, K):
    if K == 2:
        return np.asarray([[0.        , 0.        , 0.        ],
                            [0.07843137, 0.06666667, 0.09411765]])
    elif K == 4:
        return np.asarray([[0.72156863, 0.64313725, 0.54901961],
                            [0.49019608, 0.41960784, 0.33333333],
                            [0.02745098, 0.        , 0.        ],
                            [0.17254902, 0.16862745, 0.18823529]])
    elif K == 8:
        return np.asarray([[0.01568627, 0.01176471, 0.03529412],
                            [0.14509804, 0.12156863, 0.12941176],
                            [0.4745098 , 0.40784314, 0.32941176],
                            [0.00784314, 0.00392157, 0.02745098],
                            [0.50588235, 0.43529412, 0.34117647],
                            [0.09411765, 0.09019608, 0.11372549],
                            [0.54509804, 0.45882353, 0.36470588],
                            [0.44705882, 0.37647059, 0.29019608]])
    elif K == 16:
        return np.asarray([[0.61568627, 0.56078431, 0.45882353],
                            [0.4745098 , 0.38039216, 0.33333333],
                            [0.65882353, 0.57647059, 0.49411765],
                            [0.08235294, 0.07843137, 0.10196078],
                            [0.06666667, 0.03529412, 0.02352941],
                            [0.08235294, 0.07843137, 0.09803922],
                            [0.0745098 , 0.07058824, 0.09411765],
                            [0.01960784, 0.01960784, 0.02745098],
                            [0.00784314, 0.00784314, 0.01568627],
                            [0.8627451 , 0.78039216, 0.69803922],
                            [0.60784314, 0.52156863, 0.42745098],
                            [0.01960784, 0.01176471, 0.02352941],
                            [0.78431373, 0.69803922, 0.60392157],
                            [0.30196078, 0.21568627, 0.1254902 ],
                            [0.30588235, 0.2627451 , 0.24705882],
                            [0.65490196, 0.61176471, 0.50196078]])
    else:
        print('This value of K is not supported.')
        return None


def print_cent(cent):
    if type(cent) == list:
        cent = np.asarray(cent)
    if len(cent.shape) == 1:
        return ' '.join(str(np.floor(100*cent)/100).split()).replace('[ ', '[').replace('\n', ' ').replace(' ]',']').replace(' ', ', ')
    else:
        return ' '.join(str(np.floor(100*cent)/100).split()).replace('[ ', '[').replace('\n', ' ').replace(' ]',']').replace(' ', ', ')[1:-1]

def createImage(A,centroids):
    sum = 0
    for index1, pixel in enumerate(A):
        for index2, z in enumerate(pixel):
            dis = []
            for i in range(K):
                dis.append(math.pow(distance.euclidean(z, centroids[i])),2)
            sum += math.pow(distance.euclidean(A[index1][index2],centroids[dis.index(min(dis))]),2)
            A[index1][index2] = centroids[dis.index(min(dis))]
    plt.imshow(A)
    plt.grid(True)
    plt.show()

def calcualteLoss(A,centroids):
    sum = 0
    count = 0
    for index1, pixel in enumerate(A):
        for index2, z in enumerate(pixel):
            count +=1
            dis = []
            for i in range(K):
                dis.append(math.pow(distance.euclidean(z, centroids[i]),2))
            sum += math.pow(distance.euclidean(A[index1][index2], centroids[dis.index(min(dis))]),2)
            A[index1][index2] = centroids[dis.index(min(dis))]
    print("Loss:",sum/count)

Ks = [2,4,8,16]
for K in Ks:
    k = str("k=" + str(K) + ":")
    print(k)
    centroids = init_centroids(0, K)

    for i in range(11):

        ##################################
        iter = "iter " + str(i) + ": "
        print(iter, end='')
        print(print_cent(centroids))

        #calcualteLoss(A_norm.copy(),centroids)
        ###################################
        newPoints = []
        for i in range(K):
            newPoints.append([])

        for pixel in X:
            dis = []
            for i in range(K):
                dis.append(math.pow(distance.euclidean(pixel,centroids[i]),2))
            newPoints[dis.index(min(dis))].append(pixel)

        for centroid in range (K):
            centroids[centroid] = np.mean(newPoints[centroid], axis=0)

    #createImage(A_norm.copy(), centroids)


