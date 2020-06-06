'''
This file should be runnable to print map_statistics using 
$ python stats.py
'''

from collections import namedtuple, Counter
from ways import load_map_from_csv
from ways.info import ROAD_TYPES


def countLinks(road):
  return len(road.links)

def map_statistics(roads):
    '''return a dictionary containing the desired information
    You can edit this function as you wish'''
    Stat = namedtuple('Stat', ['max', 'min', 'avg'])
    #Number of junctions
    Numjunctions = len(roads.junctions())

    #Number of links
    NumLinks = 0
    NumLinks = countLinks

    #Outgoing branching factor
    branchingMax = len(max(roads.junctions(), key= lambda  road:len(road.links)).links)
    branchingMin = len(min(roads.junctions(), key= lambda  road:len(road.links)).links)
    branchingAvg = NumLinks / Numjunctions

    #Link distance
    distanceMax = max(roads.iterlinks(), key = lambda link: link.distance).distance
    distanceMin = min(roads.iterlinks(), key=lambda link: link.distance).distance
    distanceAvg = sum(link.distance for link in roads.iterlinks()) / NumLinks

    #Link type histogram
    cnt = Counter(link.highway_type for link in roads.iterlinks())
    histogram = dict(cnt)

    return {
        'Number of junctions' : Numjunctions,
        'Number of links' : NumLinks,
        'Outgoing branching factor' : Stat(max=branchingMax, min=branchingMin, avg=branchingAvg),
        'Link distance' : Stat(max=distanceMax, min=distanceMin, avg=distanceAvg),
        # value should be a dictionary
        # mapping each road_info.TYPE to the no' of links of this type
        'Link type histogram' : histogram,  # tip: use collections.Counter
    }


def print_stats():
    for k, v in map_statistics(load_map_from_csv()).items():
        print('{}: {}'.format(k, v))

        
if __name__ == '__main__':
    from sys import argv
    assert len(argv) == 1
    print_stats()
