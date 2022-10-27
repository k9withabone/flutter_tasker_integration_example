import 'package:flutter/material.dart';

import '../tasker/tasker_event_pigeon.dart';

class TaskerEventConfigPage extends StatefulWidget {
  final String title;
  final TaskerEventInput input;

  const TaskerEventConfigPage({
    super.key,
    required this.title,
    required this.input,
  });

  @override
  State<TaskerEventConfigPage> createState() => _TaskerEventConfigPageState();
}

class _TaskerEventConfigPageState extends State<TaskerEventConfigPage> {
  late final TaskerEventInput input;
  late final TextEditingController controller;

  @override
  void initState() {
    super.initState();
    input = widget.input;
    controller = TextEditingController(text: input.config);
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        if (input.config?.isNotEmpty ?? false) {
          await TaskerEventConfigApi().configDone(input);
        }
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: Padding(
          padding: const EdgeInsets.all(24),
          child: TextField(
            controller: controller,
            decoration: const InputDecoration(
              labelText: 'Event Config',
            ),
            onChanged: (value) {
              setState(() {
                input.config = value;
              });
            },
          ),
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
