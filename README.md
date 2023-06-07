# Qlearning_Sma

- The class **`Qlearning`** initializes the Q-learning algorithm with the necessary parameters such as the learning rate (`ALPHA`), discount factor (`GAMMA`), maximum number of epochs (`MAX_EPOCH`), grid size (`GRID_SIZE`), and action size (`ACTION_SIZE`).

- The `actions` array stores the possible actions that can be taken in the grid. 

# SMA Package :

It is an updated version of the code that incorporates the JADE (Java Agent Development Framework) library to implement the Q-learning algorithm in a multi-agent system.

- The **QlearningAgent** class extends the Agent class  and overrides the setup() method, which is called when the agent is initialized.

The setup() method sets up the agent's behaviors using a SequentialBehaviour. The behaviors are executed sequentially

+ The **ResetStateBehaviour** class is a one-shot behavior that resets the state of the agent.

+ The **QLearningBehaviour** class extends TickerBehaviour, which allows it to execute periodically. It implements the Q-learning algorithm similar to the original code but in the onTick() method, which is called on each tick of the behavior.

+ The **ShowResultsBehaviour** class is a one-shot behavior that displays the Q-table and performs actions based on the learned policy.
