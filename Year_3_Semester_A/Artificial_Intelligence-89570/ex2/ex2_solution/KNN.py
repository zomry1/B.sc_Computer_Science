def hamming_distance(a, b):
    return sum(c1 != c2 for c1, c2 in zip(a, b))  # Calculate hamming distance of categorical attributes


def get_neighbors(dataset, check, number):
    distances = list()
    for row in dataset:
        distance = hamming_distance(check, row[:-1])
        distances.append((row, distance))
    distances.sort(key=lambda tup: tup[1])
    neighbors = list()
    for i in range(number):
        neighbors.append(distances[i][0])
    return neighbors


def knn(dataset, check):
    neighbors = get_neighbors(dataset, check, 5)
    output = [row[-1] for row in neighbors]
    return max(set(output), key=output.count)