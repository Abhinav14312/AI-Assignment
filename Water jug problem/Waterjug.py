from collections import deque
CAP_A = 4
CAP_B = 3
def is_goal(state):
    return state[0] == 2
def get_next_states(state):
    A, B = state
    next_states = []
    next_states.append((CAP_A, B))
    next_states.append((A, CAP_B))
    next_states.append((0, B))
    next_states.append((A, 0))
    pour = min(A, CAP_B - B)
    next_states.append((A - pour, B + pour))
    pour = min(B, CAP_A - A)
    next_states.append((A + pour, B - pour))
    return next_states
def bfs():
    start = (0, 0)
    queue = deque([(start, [start])])
    visited = set()
    visited.add(start)
    while queue:
        current, path = queue.popleft()
        if is_goal(current):
            return path
        for next_state in get_next_states(current):
            if next_state not in visited:
                visited.add(next_state)
                queue.append((next_state, path + [next_state]))
    return None
solution = bfs()
if solution:
    print("Solution Path:")
    for step in solution:
        print(step)
else:
    print("No solution found.")
