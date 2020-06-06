from math import log2 as log

def choose_attribute(examples, attributes):
    best_attr = attributes[0]
    max_gain = 0
    for attribute in attributes:
        new_gain = gain(examples, attributes, attribute)
        if new_gain > max_gain:
            max_gain = new_gain
            best_attr = attribute
    return best_attr

def gain(examples, attributes, attribute):
    val_freq = {}  # We want to get a dictionary when the key is the value of the attribute and the value is number of
    # occurrences of this value
    subset_entropy = 0.0

    i = attributes.index(attribute)  # Get the index of the attribute

    for example in examples:  # Foreach example
        if example[i] in val_freq:  # Check if there is already this value (of the attribute) in the dictionary just add 1
            val_freq[example[i]] += 1
        else:  # This is a new value for this attribute
            val_freq[example[i]] = 1

    for value in val_freq.keys():  # Foreach value of the attribute
        value_probability = val_freq[value] / sum(val_freq.values())  # Calculate the probability of this value
        examples_subset = [example for example in examples if
                           example[i] == value]  # Get all examples when there this attribute has this value
        subset_entropy += value_probability * entropy(examples_subset, attributes)

    return entropy(examples, attributes) - subset_entropy

def entropy(examples, attributes):
    positive = 0
    count_examples = len(examples)
    for example in examples:
        if example[-1] == 'yes':
            positive += 1
    negative = count_examples - positive
    result = 0
    if positive != 0:
        result += (positive / count_examples) * log((positive / count_examples))
    if negative != 0:
        result += (negative / count_examples) * log((negative / count_examples))
    return -result

def getValues(examples, attributes, attribute):
    index = attributes.index(attribute)
    values = []

    for example in examples:
        if example[index] not in values:
            values.append(example[index])
    values.sort()
    return values

def getExamples(dataset, attributes, best, value):
    examples = []
    index = attributes.index(best)

    for example in dataset:
        if example[index] == value:
            new_entry = example[:index] + example[(index + 1):]
            examples.append(new_entry)

    return examples

def mode(examples):
    positive = 0
    for example in examples:
        if example[-1] == 'yes':
            positive += 1
    negative = len(examples) - positive
    if positive >= negative:
        return 'yes'
    else:
        return 'no'

def ID3(examples, attributes, attributes_values, default):
    tags = [item[-1] for item in examples]
    if not examples:  # There is no more examples
        return default
    elif tags.count(tags[0]) == len(tags):  # All the examples has the same classification
        return tags[0]
    elif not attributes:  # No more attributes
        return mode(examples)
    else:
        best_attr = choose_attribute(examples, attributes)
        tree = {best_attr: {}}

        attr_values = [value for value in attributes_values if value[0] == best_attr][0][1]

        for value in attr_values:
            new_examples = getExamples(examples, attributes, best_attr, value)
            new_attr = attributes[:]
            new_attr.remove(best_attr)
            sub_tree = ID3(new_examples, new_attr, attributes_values, mode(examples))

            tree[best_attr][value] = sub_tree

    return tree

def run_tree(tree, attributes, check):
    while type(tree) == dict:
        if sorted(tree)[0] in attributes:
            index = attributes.index(sorted(tree)[0])
            tree = tree[sorted(tree)[0]][check[index]]
    return tree


def print_tree(tree, attributes, last_key, level, to_print=""):
    if type(tree) != str:  # the tree is not a leaf
        for node in sorted(tree):
            if last_key in attributes:  # If the last_key was attribute
                to_print += '\n'  # New line
                if level > 1:  # Check if '|' is needed
                    if type(tree[node]) == str:  # Check if is a leaf to print 'yes' or 'no'
                        to_print += (('\t' * (level - 2)) + '|' + last_key + '=' + node + ":" + tree[node])
                    else:
                        to_print += (('\t' * (level - 2)) + '|' + last_key + '=' + node)
                else:
                    if type(tree[node]) == str:  # Check if is a leaf to print 'yes' or 'no'
                        to_print += (('\t' * (level - 1)) + last_key + '=' + node + ":" + tree[node])
                    else:
                        to_print += (('\t' * (level - 1)) + last_key + '=' + node)

            if type(tree) == dict: #If it's a tree too, call recursively
                if node not in attributes and level > 1:
                    to_print += print_tree(tree[node], attributes, node, level)
                else:
                    to_print += print_tree(tree[node], attributes, node, level + 1)
    return to_print

