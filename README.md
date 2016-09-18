# JavaUpdater
A library that can be put into a program which will run auto updates for the jar. 

Known Bugs/Problems

On Mac:

1. Becuase of the JOptionPane used in the updater, when certain Swing items are shown within the application that has been
updated, the iten will have to be initilized and shown in this block of code:

	EventQueue.invokeAndWait(new Runnable() {
		@Override
		public void run() {
		}
	};
