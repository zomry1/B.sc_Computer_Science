import csv

from RoutingProblem import RoutingProblem
from algorithms import best_first_graph_search
from ways import load_map_from_csv

# Instead line 22
# print("cost: " + str(node.path_cost))
# return node.path_cost  ##REMEMBER TO DELETE THIS AFTER USING MULTIPLE PROBLEMS

def find_ucs_routeMul(source, target,map):
    problem = RoutingProblem(source ,target, map)
    return uniform_cost_search(problem)

def uniform_cost_search(problem):
    def g(node):
        return node.path_cost

    return best_first_graph_search(problem, f=g)


file = open("results/UCSRuns.txt", "w")
count = 0
loaded_map = load_map_from_csv()
with open('problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        cost = find_ucs_routeMul(int(row[0]), int(row[1]), loaded_map)
        print(cost)
        file.write(str(cost))
        file.write('\r')
        print("-------------------------------------------------------------------------------------")
        count += 1
