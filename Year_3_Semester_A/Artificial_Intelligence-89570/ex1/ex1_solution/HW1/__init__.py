import pandas as pd
import json
from collections import deque
import heapq

from functools import total_ordering


# dict on python 3.7+ preserves insertion order.
# This is a quick way to create a set which preserves it as well.
# required for presentation purposes only.
def ordered_set(coll):
  return dict.fromkeys(coll).keys()

def swap_tuple(a_tuple, i, j):
  l = list(a_tuple)
  l[i],l[j] = l[j],l[i]
  return tuple(l)

def load_routes(routes, symmetric=True):
  def insert(frm,to,cost):
    if frm in G:
      G[frm][to]=cost
    else:
      G[frm] = {to:cost}
  G = {}
  routes = routes.splitlines()
  for route in routes:
    r = route.split(',')
    insert(r[0],r[1],int(r[2]))
    if symmetric:
      insert(r[1],r[0],int(r[2]))
  return G