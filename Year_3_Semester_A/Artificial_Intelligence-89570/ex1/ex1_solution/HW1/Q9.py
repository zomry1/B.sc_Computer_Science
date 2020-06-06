import csv

from Node import Node
from RoutingProblem import RoutingProblem
from algorithms import best_first_graph_search, heuristic
from ways import load_map_from_csv
import matplotlib.pyplot as plt

# Instead line 22
# print("cost: " + str(node.path_cost))
# return node.path_cost  ##REMEMBER TO DELETE THIS AFTER USING MULTIPLE PROBLEMS

def find_astar_routeMul(source, target, map):
    problem = RoutingProblem(source, target, map)
    return astar_search(problem)

def astar_search(problem):
    def g(node):
        return node.path_cost

    #print("heuristic cost: ", heuristic(Node(problem.s_start),problem))
    return best_first_graph_search(problem, f=lambda n: g(n)+heuristic(n, problem))


file = open("results/AStarRuns.txt", "w")
count = 0
x = []
y = []
loaded_map = load_map_from_csv()
with open('problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        cost = find_astar_routeMul(int(row[0]), int(row[1]), loaded_map)
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

#Draw the graph
plt.plot(x,y, 'ro')
plt.xlabel('heuristic cost')
plt.ylabel('Real cost')
plt.show()


