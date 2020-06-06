import torch.nn as nn
import torch.nn.functional as F

from gcommand_loader import GCommandLoader
import torch

dataset = GCommandLoader('ML4_dataset/data/train')

trainloader = torch.utils.data.DataLoader(
        dataset, batch_size=1, shuffle=True,
        num_workers=20, pin_memory=True, sampler=None)


validset = GCommandLoader('ML4_dataset/data/valid')

validloader = torch.utils.data.DataLoader(
        validset, batch_size=1, shuffle=True,
        num_workers=20, pin_memory=True, sampler=None)

testset = GCommandLoader('test')

testloader = torch.utils.data.DataLoader(
        testset, batch_size=1, shuffle=True,
        num_workers=20, pin_memory=True, sampler=None)


class Net(nn.Module):
    def __init__(self):
        super(Net, self).__init__()
        self.conv1 = nn.Conv2d(1, 6, 6,5) 
        self.conv1_5 = nn.Conv2d(6,16,5)
        #self.pool = nn.MaxPool2d(2, 2) #Output of 16X10
        self.conv2 = nn.Conv2d(16, 32, 5)#Output 16 layers, conv of 5X5 - 12X6
        self.dropout = nn.Dropout()
        self.fc1 = nn.Linear(32 * 24 * 12, 2048)
        self.fc1_5 = nn.Linear(2048,512)
        self.fc2 = nn.Linear(512, 128)
        self.fc3 = nn.Linear(128, 30)

    def forward(self, x):
        #Image size we have is 161X101 
        #x = self.pool(F.relu(self.conv1(x)))# after convolution 6X32X20 - after pool 6X16X10
        #x = self.pool(F.relu(self.conv2(x)))# after convlution 16X12X6 - after pool 16X6X3
        
        
        x = F.relu(self.conv1(x))# after convolution 6X32X20 
        #x = self.pool(x) #after pool 6X16X10
        x = F.relu(self.conv1_5(x))# after convolution 16X28X16
        x = F.relu(self.conv2(x))# after convlution 32X24X12 
        
        
        x = x.view(-1, 32 * 24 * 12)
        x = self.dropout(x)
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc1_5(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return x


net = Net()
net.to(device)


import torch.optim as optim

criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(net.parameters(), lr=0.000055)

for epoch in range(10):  # loop over the dataset multiple times

    running_loss = 0.0
    for i, data in enumerate(trainloader, 0):
        # get the inputs; data is a list of [inputs, labels]
        #inputs, labels = data 
        inputs, labels = data[0].to(device), data[1].to(device) #using cuda

        # zero the parameter gradients
        optimizer.zero_grad()

        # forward + backward + optimize
        outputs = net(inputs)
        loss = criterion(outputs, labels)
        loss.backward()
        optimizer.step()

        # print statistics
        running_loss += loss.item()
        if i % 2000 == 1999:    # print every 2000 mini-batches
            print('[%d, %5d] loss: %.3f' %
                  (epoch + 1, i + 1, running_loss / 2000))
            running_loss = 0.0
    
    correct = 0
    total = 0
    with torch.no_grad():
      for i,data in enumerate(validloader, 0):
          #images, labels = data
          images, labels = data[0].to(device), data[1].to(device) #using cuda
          outputs = net(images)
          _, predicted = torch.max(outputs.data, 1)
          total += labels.size(0)
          correct += (predicted == labels).sum().item()

    print('Accuracy of the network on the valid sounds: %d %%' % (
        100 * correct / total))

print('Finished Training')


correct = 0
total = 0
with torch.no_grad():
    for i,data in enumerate(validloader, 0):
        #images, labels = data
        images, labels = data[0].to(device), data[1].to(device) #using cuda
        outputs = net(images)
        _, predicted = torch.max(outputs.data, 1)
        total += labels.size(0)
        correct += (predicted == labels).sum().item()

print('Accuracy of the network on the valid sounds: %d %%' % (
    100 * correct / total))

filepath = 'test_y'
with torch.no_grad():
    for i,data in enumerate(testloader, 0):
        #images, labels = data
        images, labels = data[0].to(device), data[1].to(device) #using cuda
        outputs = net(images)
        _, predicted = torch.max(outputs.data, 1)
        toprint = str(data[2]) + ", " + str(predicted.tolist()[0])
        print(toprint)
        with open(filepath, 'a') as fp:  
          fp.write(toprint+'\r\n')

print('finished')