# Omri Zur, 206575854

import hashlib

'''################### Classes ###################'''


class MerkelLeaf(object):
    def __init__(self, data):
        self.hash = data
        self.height = 0

    def get_hash(self):
        return self.hash

    def get_height(self):
        return self.height


class MerkelNode(object):
    def __init__(self, child_left, child_right):
        self.children = [child_left, child_right]

    def get_hash(self):
        hash_str = ''
        for child in self.children:
            hash_str += child.get_hash()
        return hashlib.sha256(hash_str.encode()).hexdigest()

    def get_height(self):
        return self.children[0].get_height() + 1


'''################### Utils ###################'''


def format_proof(proof_str):
    proof = []
    words = proof_str.split()
    if len(words) % 2 != 0:  # Proof not in format
        exit()
    for side, hash in zip(words[0::2], words[1::2]):
        if side != 'r' and side != 'l':  # Proof not in format
            exit()
        proof.append([side, hash])
    return proof


def find_direction(index, root):
    if index < pow(2, root.get_height()) / 2:  # The leaf is on the left side
        return 'l'
    else:  # Leaf is on the right side
        return 'r'


'''################### Exercise Input ###################'''


def create_tree(input):  # Input 1
    # Read the nodes and create leaves
    nodes = []
    for leaf in input.split():
        nodes.append(MerkelLeaf(leaf))

    # Create tree
    while len(nodes) != 1:
        i = 0
        left_children = nodes[0::2]  # index % 2 == 0
        right_children = nodes[1::2]  # index % 2 == 1
        nodes.clear()
        for child1, child2 in zip(left_children, right_children):
            nodes.append(MerkelNode(child1, child2))
            i += 1

    return nodes[0]  # Return the root node


def get_proof(index, root):  # Input 2
    if index > (pow(2, root.get_height()) - 1):  # Index out of tree
        exit()

    proof = []
    while not isinstance(root, MerkelLeaf):
        side = find_direction(index, root)  # Get the direction for the leaf

        if side == 'l':
            proof.append(['r', root.children[1].get_hash()])  # Add the right side to the proof
            root = root.children[0]  # Move to the left child
        else:
            proof.append(['l', root.children[0].get_hash()])  # Add the left side to the proof
            root = root.children[1]  # Move to the right child
            index = index - (pow(2, root.get_height()) / 2)  # Update index

    # Format the proof
    proof_str = ''
    for step in reversed(proof):  # Loop reversed order
        proof_str += ' ' + step[0] + ' ' + step[1]

    return proof_str.lstrip()  # Remove leading spaces


def verify_proof(leaf, root_hash, proof):  # Input 3
    for step in proof:  # Each step is tuple of (side, hash)
        if step[0] == 'l':
            leaf = hashlib.sha256((step[1] + leaf).encode()).hexdigest()
        else:
            leaf = hashlib.sha256((leaf + step[1]).encode()).hexdigest()
    if leaf == root_hash:
        return True
    return False


def find_nonce(zeros, root_hash):
    new_hash = root_hash
    nonce = 0
    while len(new_hash) - len(new_hash.lstrip('0')) < zeros:  # While not enough leading zeros found
        new_hash = hashlib.sha256((str(nonce) + root_hash).encode()).hexdigest()
        nonce += 1
    answer_str = str(nonce - 1) + ' ' + new_hash
    return answer_str


if __name__ == '__main__':
    root = None
    command_number = 0
    while command_number != 5:
        # Read input and parse to command number and arguments
        command = input()
        command_number = int(command.split()[0])
        command_arguments = ' '.join(command.split()[1:])

        if command_number == 1:  # Create a tree
            root = create_tree(command_arguments)
            print(root.get_hash())

        elif command_number == 2:  # Create a proof
            if root is None:  # Tree is not existing
                exit()

            try:
                index = int(command_arguments)
            except:  # When index is not a number
                exit()
            print(get_proof(index, root))

        elif command_number == 3:  # Verify proof
            leaf = command_arguments.split()[0]
            root_hash = command_arguments.split()[1]
            proof = command_arguments.split()[2:]
            proof = [(proof[i], proof[i + 1]) for i in range(0, len(proof), 2)]  # Create tuple of direction and hash
            print(verify_proof(leaf, root_hash, proof))

        elif command_number == 4:  # Find nonce
            if root is None:  # Tree is not existing
                exit()

            try:
                zeros = int(command_arguments)
            except:  # When zeros is not a number
                exit()
            print(find_nonce(zeros, root.get_hash()))

        elif command_number == 5:
            exit()
