import 'package:flutter/material.dart';

import '../tasker/tasker_event_pigeon.dart';
import '../tasker/tasker_state_pigeon.dart';

class HomePage extends StatefulWidget {
  final String title;

  const HomePage({super.key, required this.title});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late final TextEditingController controller;

  @override
  void initState() {
    super.initState();
    controller = TextEditingController();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(32),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            TextField(
              controller: controller,
              decoration: const InputDecoration(
                labelText: 'Event Update',
              ),
            ),
            ElevatedButton(
              onPressed: () async {
                final scaffoldMessenger = ScaffoldMessenger.of(context);
                final update = TaskerEventUpdate(update: controller.text);
                await TaskerEventTriggerApi().triggerEvent(update);
                scaffoldMessenger.showSnackBar(const SnackBar(
                  content: Text('Triggered Tasker Event!'),
                ));
              },
              child: const Text('Trigger Tasker Event'),
            ),
            ElevatedButton(
              onPressed: () async {
                final scaffoldMessenger = ScaffoldMessenger.of(context);
                await TaskerStateUpdateApi()
                    .setState(TaskerStateCondition.toggle);
                scaffoldMessenger.showSnackBar(const SnackBar(
                  content: Text('Toggled Tasker State!'),
                ));
              },
              child: const Text('Toggle Tasker State'),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    controller.dispose();
  }
}
