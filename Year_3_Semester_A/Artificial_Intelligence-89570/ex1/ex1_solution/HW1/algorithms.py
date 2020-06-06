from Node import Node
from PriorityQueue import PriorityQueue
from RoutingProblem import RoutingProblem
from ways import load_map_from_csv
from ways.tools import compute_distance
import math
import time
#Global
new_limit = math.inf


def best_first_graph_search(problem, f):
    node = Node(problem.s_start)
    frontier = PriorityQueue(f)
    frontier.append(node)
    closed_list = set()
    while frontier:
        if len(closed_list) % 1000 == 0:
            print(f'size of closed list:{len(closed_list)}')
        node = frontier.pop()
        if problem.is_goal(node.state):
            return node.solution() + [problem.goal]
        closed_list.add(node.state)

        for child in node.expand(problem):
            if child.state not in closed_list and child not in frontier:
                frontier.append(child)
            elif child in frontier and f(child) < frontier[child]:
                del frontier[child]
                frontier.append(child)
    return None


def heuristic(node, problem):
    junction_start = problem.G[node.state]
    junction_end = problem.G[problem.goal]
    return compute_distance(junction_start.lat, junction_start.lon, junction_end.lat, junction_end.lon) / 110


def uniform_cost_search(source, target):
    def g(node):
        return node.path_cost

    problem = RoutingProblem(source, target, load_map_from_csv())
    return best_first_graph_search(problem, f=g)


def astar_search(source, target):
    def g(node):
        return node.path_cost

    problem = RoutingProblem(source, target, load_map_from_csv())
    print("heuristic cost: ", heuristic(Node(source), problem))
    return best_first_graph_search(problem, f=lambda n: g(n) + heuristic(n, problem))


def ida_star(source, target):
    global new_limit
    problem = RoutingProblem(source, target, load_map_from_csv())
    new_limit = heuristic(Node(source), problem)
    while True:
        f_limit = new_limit
        new_limit = math.inf
        sol = dfs_f(Node(problem.s_start), 0, f_limit, problem)
        if sol:
            return sol


def dfs_f(node, g, f_limit, problem):
    global new_limit
    new_f = g + heuristic(node, problem)
    if new_f > f_limit:
        new_limit = min(new_limit, new_f)
        return None
    if problem.is_goal(node.state):
        path = node.solution()
        path.append(problem.goal)
        return path
    for child in node.expand(problem):
        sol = dfs_f(child, g + (child.path_cost-node.path_cost), f_limit, problem)
        if sol:
            return sol
    return None
