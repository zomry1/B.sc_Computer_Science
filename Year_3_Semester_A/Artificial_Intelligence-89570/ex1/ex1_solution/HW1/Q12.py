import csv

from RoutingProblem import RoutingProblem
from algorithms import best_first_graph_search
from ways import load_map_from_csv
from ways.draw import plot_path
import matplotlib.pyplot as plt


def find_ucs_routeMul(source, target, map):
    problem = RoutingProblem(source, target, map)
    return uniform_cost_search(problem)


def uniform_cost_search(problem):
    def g(node):
        return node.path_cost

    return best_first_graph_search(problem, f=g)


folder = 'solutions_img'
count = 0
loaded_map = load_map_from_csv()
with open('problems.csv') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print("----------------Problem[", count, "] start ", row[0], " goal ", row[1], "---------------")
        path = find_ucs_routeMul(int(row[0]), int(row[1]), loaded_map)
        print(path)
        fig = plt.gcf()
        plot_path(loaded_map, path)
        plt.show()
        fig.savefig(folder + '/solution-' + str(count) + '-' + row[0] + '-' + row[1] + 'png', bbox_inches='tight')
        plt.close()
        count += 1
