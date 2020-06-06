from ways import graph
from random import randrange
import csv

# Need to remove line spaces from the file


def create_csv(roads):
    counter = 0
    maxNumber = len(roads)
    with open('problems.csv', 'w') as file:
        writer = csv.writer(file, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        while True:
            if counter == 100:
                break
            source = randrange(maxNumber)
            target = randrange(maxNumber)
            if check_possibility(roads, source, target):
                writer.writerow([str(source), str(target)])
                counter += 1


def adj(roads, v):
    succ = []
    links = roads.get(v).links
    for k in links:
        succ.append(k[1])
    return succ


def bfs_search(roads, source_id):
    closed_list = [False] * (len(roads))
    queue = [source_id]
    closed_list[source_id] = True
    while queue:
        u = queue.pop(0)
        succ = adj(roads, u)
        for v in succ:
            if not closed_list[v]:
                queue.append(v)
                closed_list[v] = True
    return closed_list


def check_possibility(roads, source_id, target_id):
    visited = bfs_search(roads, source_id)
    if visited[target_id]:
        return True
    else:
        return False


r = graph.load_map_from_csv()
create_csv(r)