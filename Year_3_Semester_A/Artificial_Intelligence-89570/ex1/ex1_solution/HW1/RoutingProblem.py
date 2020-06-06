from ways import graph
from ways.info import SPEED_RANGES


class RoutingProblem:

    def __init__(self, s_start, goal, G):
        self.s_start = s_start
        self.goal = goal
        self.G = G

    def actions(self, s):
        return self.G[s].links

    def succ(self, s, a):
        if a.source == s:
            return a.target
        # if a in self.G[s]:
        #    return a
        raise ValueError(f'No route from {s} to {a}')

    def is_goal(self, s):
        return s == self.goal

    def step_cost(self, s, a):
        return (a.distance / 1000) / SPEED_RANGES[a.highway_type][1]

    def state_str(self, s):
        return s

    def __repr__(self):
        return {'s_start': self.s_start, 'goal': self.goal, graph: 'self.G'}
