import random

def gen_num(seed, lower, upper, iteration=0):
    random.seed(seed)
    for _ in range(iteration):
        random.randint(lower, upper)
    return random.randint(lower, upper)