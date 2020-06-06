def multiply(numbers):
    x = 1
    for num in numbers:
        x *= num
    return x


# Count labels (yes, no) for attribute
def count_attribute(dataset, values, attribute):
    # Create the dictionaries
    dic = {'yes': {}, 'no': {}}
    for value in values[attribute][1]:
        dic['yes'][value] = 0
        dic['no'][value] = 0

    # For each example add 1 to the match dictionary
    for example in dataset:
        dic[example[-1]][example[attribute]] += 1
    return dic


# For each attribute make a dictionary
def count_all(dataset, values):
    counts = []
    for i in range(len(values)):
        counts.append(count_attribute(dataset, values, i))
    return counts


# Count each label (yes, no)
def count_labels(dataset):
    labels = [row[-1] for row in dataset]
    yes = labels.count('yes')
    no = len(labels) - yes
    return (yes, no)


# Calculate each value his probability
def calculate_probabilities(counts, count_yes, count_no):
    probabilities = {'yes': {}, 'no': {}}
    i = 0
    for dic in counts:
        for label, values in zip(dic.keys(), dic.values()):
            if label == 'yes':
                sum_label = count_yes
            else:
                sum_label = count_no
            probabilities[label][i] = {}
            for value, count in zip(values.keys(), values.values()):
                probabilities[label][i][value] = float(count + 1) / (sum_label + len(values.values()))
        i += 1
    return probabilities


# Train on the dataset
def train_naive_bayes(dataset, values):
    count_yes, count_no = count_labels(dataset)
    counts = count_all(dataset, values)
    probabilities = calculate_probabilities(counts, count_yes, count_no)

    return probabilities


# Check for example
def check_naive_bayes(dataset, probabilities, check):
    count_yes, count_no = count_labels(dataset)

    prob_yes = multiply([value[check[i]] for i, value in probabilities['yes'].items()]) \
               * (count_yes / (count_yes + count_no))
    prob_no = multiply([value[check[i]] for i, value in probabilities['no'].items()]) \
              * (count_yes / (count_yes + count_no))
    if prob_yes >= prob_no:
        return 'yes'
    else:
        return 'no'
