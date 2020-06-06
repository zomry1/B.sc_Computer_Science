import csv

from Node import Node
from RoutingProblem import RoutingProblem
from algorithms import heuristic
from main import find_idastar_route
from ways import load_map_from_csv
import matplotlib.pyplot as plt

# Instead line 78
# return [node.path_cost]

file = open("results/IDAStarRuns.txt", "w")
count = 0
x = []
y = []
loaded_map = load_map_from_csv()
with open('5problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        cost = find_idastar_route(int(row[0]), int(row[1]))
        problem = RoutingProblem(int(row[0]), int(row[1]), loaded_map)
        heuristica = heuristic(Node(problem.s_start),problem)
        line = "cost: " +  str(cost) + " heuristic: " + str(heuristica)
        x.append(heuristica)
        y.append(cost)
        print(line)
        file.write(line)
        file.write('\r')
        print("-------------------------------------------------------------------------------------")
        count += 1
        if count == 5:
            break

#Draw the graph
plt.plot(x,y, 'ro')
plt.xlabel('heuristic cost')
plt.ylabel('Real cost')
plt.show()


