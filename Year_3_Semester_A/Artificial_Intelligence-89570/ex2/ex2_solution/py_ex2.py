from Decision_Tree import print_tree, ID3, mode, run_tree
from Naive_Bayes import train_naive_bayes, check_naive_bayes
from KNN import knn


def load_data(filename):
    dataset = []
    file = open(filename, 'r')
    next(file)  # Skip first line
    for line in file:
        dataset.append(line.replace('\n', '').split('\t'))
    return dataset


def load_attributes(filename, data):
    file = open(filename, 'r')
    attributes = file.readline()
    attributes = attributes.replace('\n', '').split('\t')[:-1]
    num_columns = len(attributes)
    values = []
    for i in range(num_columns):
        column = [row[i] for row in data]
        values.append((attributes[i], list(set(column))))
    return attributes, values


############################################################################
def cross_validation(dataset, folds=5):
    lines_len = len(dataset)
    batch_size = int(lines_len / 5)
    first_batch = dataset[0:batch_size]
    second_batch = dataset[batch_size:2 * batch_size]
    third_batch = dataset[2 * batch_size:3 * batch_size]
    forth_batch = dataset[3 * batch_size:4 * batch_size]
    fifth_batch = dataset[4 * batch_size:lines_len]
    return [first_batch, second_batch, third_batch, forth_batch, fifth_batch]

def check_training_KNN(dataset, num_folds=5):
    folds = cross_validation(dataset)
    count_mistakes = 0
    for i in range(num_folds):  # Each time the i fold will be the test
        train = [x for j, x in enumerate(folds) if j != i]  # take the other folds to train
        train = [item for sublist in train for item in sublist]  # make all folds to one list
        test = folds[i]

        for example in test:
            true_answer = example[-1]
            example = example[:-1]
            answer = knn(train, example)
            if answer != true_answer:
                count_mistakes += 1
    return ((len(dataset) - count_mistakes) / len(dataset))


def check_training_naive_bayes(dataset, values,  num_folds=5):
    folds = cross_validation(dataset)
    count_mistakes = 0
    for i in range(num_folds):  # Each time the i fold will be the test
        train = [x for j, x in enumerate(folds) if j != i]  # take the other folds to train
        train = [item for sublist in train for item in sublist]  # make all folds to one list
        test = folds[i]

        probabilities = train_naive_bayes(train,values)
        for example in test:
            true_answer = example[-1]
            example = example[:-1]
            answer = check_naive_bayes(dataset, probabilities, example)
            if answer != true_answer:
                count_mistakes += 1
    return ((len(dataset) - count_mistakes) / len(dataset))


def check_decision_tree(dataset, attributes, values, num_folds=5):
    folds = cross_validation(dataset)
    #folds = cross_validation_split(dataset, num_folds)

    count_mistakes = 0
    for i in range(num_folds):  # Each time the i fold will be the test
        train = [x for j, x in enumerate(folds) if j != i]  # take the other folds to train
        train = [item for sublist in train for item in sublist]  # make all folds to one list
        test = folds[i]

        tree = ID3(train, attributes, values, mode(train))
        for example in test:
            true_answer = example[-1]
            example = example[:-1]
            answer = run_tree(tree, attributes, example)
            if answer != true_answer:
                count_mistakes += 1
    return ((len(dataset) - count_mistakes) / len(dataset))


data = load_data('dataset.txt')
attributes, values = load_attributes('dataset.txt', data)

#Print tree to file
tree = ID3(data, attributes, values, mode(data))
with open( 'tree.txt', 'w') as tree_file:
    tree_file.write(print_tree(tree, attributes, "", 0)[1:])

#Get accuracy and write it to file
knn_acc = check_training_KNN(data, 5)
knn_acc = '{:.2f}'.format(knn_acc)
naive_acc = check_training_naive_bayes(data, values,  5)
naive_acc = '{:.2f}'.format(naive_acc)
decision_acc = check_decision_tree(data, attributes, values, 5)
decision_acc = '{:.2f}'.format(decision_acc)
with open( 'accuracy.txt', 'w') as accuracy_file:
    accuracy_file.write(decision_acc + '\t' + knn_acc + '\t' + naive_acc)

##############################################################################

#Load data
data_train = load_data('train.txt')
data_test = load_data('test.txt')
attributes_train, values_train = load_attributes('train.txt', data_train)

#Train models (naive bayes and tree)
tree_train = ID3(data_train, attributes_train, values_train, mode(data_train))
probabilities = train_naive_bayes(data_train, values_train)

#Iterate on the test and count mistakes
mistakes_tree = 0
mistakes_knn = 0
mistakes_naive = 0
for example in data_test:
    true_answer = example[-1] #Get the true answer
    example = example[:-1] #Get the values of the attributes without the tag

    answer_tree = run_tree(tree_train, attributes_train, example)
    answer_knn = knn(data_train, example)
    answer_naive = check_naive_bayes(data_train, probabilities, example)
    file.write(answer_naive + '\n')
    if answer_tree != true_answer:
        mistakes_tree += 1
    if answer_knn != true_answer:
        mistakes_knn += 1
    if answer_naive != true_answer:
        mistakes_naive += 1

decision_acc = ((len(data_test) - mistakes_tree) / len(data_test))
decision_acc = '{:.2f}'.format(decision_acc)
knn_acc = ((len(data_test) - mistakes_knn) / len(data_test))
knn_acc = '{:.2f}'.format(knn_acc)
naive_acc = ((len(data_test) - mistakes_naive) / len(data_test))
naive_acc = '{:.2f}'.format(naive_acc)


with open( 'output.txt', 'w') as output_file:
    output_file.write(print_tree(tree_train, attributes_train, "", 0)[1:])
    output_file.write('\n\n')
    output_file.write(decision_acc + '\t' + knn_acc + '\t' + naive_acc)