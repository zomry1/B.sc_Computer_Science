import time
import csv

from Node import Node
from RoutingProblem import RoutingProblem
from algorithms import best_first_graph_search, heuristic
from main import find_idastar_route
from ways import load_map_from_csv
import matplotlib.pyplot as plt

def find_ucs_routeMul(source, target,map):
    problem = RoutingProblem(source ,target, map)
    return uniform_cost_search(problem)

def uniform_cost_search(problem):
    def g(node):
        return node.path_cost

    return best_first_graph_search(problem, f=g)

def find_astar_routeMul(source, target, map):
    problem = RoutingProblem(source, target, map)
    return astar_search(problem)

def astar_search(problem):
    def g(node):
        return node.path_cost

    return best_first_graph_search(problem, f=lambda n: g(n)+heuristic(n, problem))


file = open("results/UCSRunsTime.txt", "w")
count = 0
loaded_map = load_map_from_csv()
with open('problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        start = time.clock()
        path = find_ucs_routeMul(int(row[0]), int(row[1]), loaded_map)
        end = time.clock() - start
        print(end)
        file.write(str(end))
        file.write('\r')
        print("-------------------------------------------------------------------------------------")
        count += 1
file.close()

file1 = open("results/AStarRunsTime.txt", "w")
count = 0
with open('problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        start = time.clock()
        cost = find_astar_routeMul(int(row[0]), int(row[1]), loaded_map)
        end = time.clock() - start
        print(end)
        file1.write(str(end))
        file1.write('\r')
        print("-------------------------------------------------------------------------------------")
        count += 1
file.close()

file2 = open("results/IDAStartRunsTime.txt", "w")
with open('5problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        dur = find_idastar_route(int(row[0]), int(row[1]))
        print(dur)
        file2.write(str(dur))
        file2.write('\r')
        print("-------------------------------------------------------------------------------------")
        count += 1