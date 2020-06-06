from random import shuffle
from scipy import stats
import numpy as np
import sys

preceptronEta = 0.1
SVMEta = 0.1
lamda =  0.075
epchocs = 60
wave = 6

one_hot = {"M":[1,0,0], "F":[0,1,0], "I":[0,0,1]}

def loadX(data_x):
    try:
        with open(data_x, "r") as data:
            x = data.readlines()
        newX = []
        for item in x:
            item = item.rstrip()
            item = item.split(",")
            item = one_hot[item[0]] + item
            del item[3]
            item = [float(x) for x in item]
            newX.append(item)
        return newX
    except:
        print("error when load x file")
        sys.exit()

def loadY(train_y):
    try:
        train = []
        with open(train_y, "r") as data:
            line = data.readline()
            while line:
                train.append(int(line[0]))
                line = data.readline()
        return train
    except:
        print("error when load y file")
        sys.exit()

def zNormalize(train_x,test_x):
    train_x = np.array(loadX(train_x))
    length = len(train_x)
    test_x = np.array(loadX(test_x))
    x = np.vstack((train_x,test_x))
    stats.mstats.zscore(x, axis=1, ddof=1)
    train_x = x[0:length]
    test_x = x[length:]
    return train_x,test_x


def label(x,w):
    labels = []
    for item in x:
        labels.append(np.argmax(np.dot(w,item)))
    return labels

def tau(w,x,y,yHat):
    return (max(0,1-np.dot(w[y],x)+np.dot(w[yHat],x)))/(2*(np.power(np.linalg.norm(x),2)))

def preceptronUpdate(w,y,yHat,x):
    w[y] = w[y] + preceptronEta * x
    w[yHat] = w[yHat] - preceptronEta * x

def SVMUpdate(w,y,yHat,x):
    for i in range(len(w)):
        if i == y:
            w[y] = np.multiply((1 - SVMEta * lamda), w[y]) + (x * SVMEta)
        elif i == yHat:
            w[yHat] = np.multiply((1 - SVMEta * lamda), w[yHat]) - (x * SVMEta)
        else:
            w[i] = np.multiply((1 - SVMEta * lamda), w[i])

def PAUpdate(w,y,yHat,x):
    w[y] = w[y] + (tau(w, x, y,yHat) * x)
    w[yHat] = w[yHat] - (tau(w, x, y,yHat) * x)

if(len(sys.argv) != 4):
    print("Invalid arguments")
    sys.exit()

#Inialize machines weights
wPrecptron = []
wSVM = []
wPA = []


#Make few trains and take the most frequently labels
waves = []
waves.append([])
waves.append([])
waves.append([])

for j in range(wave):
    for i in range(3):
        wPrecptron.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        wSVM.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        wPA.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

    #load train data
    x_train, x_test = zNormalize(sys.argv[1],sys.argv[3])
    y_train = loadY(sys.argv[2])

    for epchoc in range(epchocs):
        # Shuffle the training set together
        traininSet = list(zip(x_train, y_train))
        shuffle(traininSet)
        x_train, y_train = zip(*traininSet)

        for x, y in zip(x_train, y_train):
            xArray = np.array(x)
            y_hatPre = np.argmax(np.dot(wPrecptron, xArray))
            y_hatSVM = np.argmax(np.dot(wSVM, xArray))
            y_hatPA = np.argmax(np.dot(wPA, xArray))
            if y != y_hatPre:
                preceptronUpdate(wPrecptron, y, y_hatPre, xArray)
            if y != y_hatSVM:
                SVMUpdate(wSVM, y, y_hatSVM, xArray)
            if y != y_hatPA:
                PAUpdate(wPA, y, y_hatPA, xArray)

    waves[0].append(label(x_test,wPrecptron))
    waves[1].append(label(x_test, wSVM))
    waves[2].append(label(x_test, wPA))

    wPrecptron = []
    wSVM = []
    wPA = []
    for i in range(3):
        wPrecptron.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        wSVM.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        wPA.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

#Take the most frequently labels
preceptron = np.swapaxes(waves[0],0,1)
preceptronLabels = []
for label in preceptron:
    preceptronLabels.append(np.argmax(np.bincount(label)))

SVM = np.swapaxes(waves[1],0,1)
SVMLabels = []
for label in SVM:
    SVMLabels.append(np.argmax(np.bincount(label)))


PA = np.swapaxes(waves[2],0,1)
PALabels = []
for label in PA:
    PALabels.append(np.argmax(np.bincount(label)))

for preceptron,SVM,PA in zip(preceptronLabels,SVMLabels,PALabels):
    toPrint = "perceptron: " + str(preceptron) + ", " + "svm: " + str(SVM) + ", " + "pa: " + str(PA)
    print(toPrint)