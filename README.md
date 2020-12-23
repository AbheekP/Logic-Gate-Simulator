# Logic-Gate-Simulator
A Java Program where you can simulate logic circuits. 

# Instructions 

## Creating a Logic Circuit

When the application is started, the main workspace area and a logic gate selection menu will be presented. 

![image](https://user-images.githubusercontent.com/76227825/103019852-6d945700-4515-11eb-9556-6018ccebc75b.png)

The first 7 selections in the selection menu are logic gates, while the last 2 selections are input and output nodes. Clicking on any of them will insert them into the middle of the workspace, where they can then be dragged around. 

![GIF 12-23-2020 12-09-17 PM](https://user-images.githubusercontent.com/76227825/103021265-e399bd80-4517-11eb-8b34-ca95f1c68f6d.gif)

In order to connect input/output nodes and logic gates together, click on the output of one component and the input of another component. A wire will then be created between the 2 components. 

![GIF 12-23-2020 12-09-17 PM](https://user-images.githubusercontent.com/76227825/103021947-01b3ed80-4519-11eb-838b-081364a7012c.gif)

Double clicking on the body of a logic gate or the connector node of the input/output nodes will delete them and all wires attached to them. 

![GIF 12-23-2020 12-09-17 PM](https://user-images.githubusercontent.com/76227825/103022261-a20a1200-4519-11eb-9f5e-aa7877ac6bcb.gif)

Double clicking on the input or output nodes of a logic gate will also delete any wires that are connected to that node.

![GIF 12-23-2020 12-09-17 PM](https://user-images.githubusercontent.com/76227825/103022852-c5818c80-451a-11eb-8310-76de74453921.gif)

## Testing a circuit

After creating a logic circuit, click on the large circle of the input nodes. This will cause the input node to turn red (indicating that it is on). All of the connected wires will also turn red and any connected logic gates will calculate their output and turn on output wires as needed. 

![GIF 12-23-2020 12-09-17 PM](https://user-images.githubusercontent.com/76227825/103025398-bbae5800-451f-11eb-9639-01aabfaf5e39.gif)

