import 'package:flutter/material.dart';

import '../tasker/tasker_action_pigeon.dart';

class TaskerActionConfigPage extends StatefulWidget {
  final String title;
  final TaskerActionInput input;

  const TaskerActionConfigPage({
    super.key,
    required this.title,
    required this.input,
  });

  @override
  State<TaskerActionConfigPage> createState() => _TaskerActionConfigPageState();
}

class _TaskerActionConfigPageState extends State<TaskerActionConfigPage> {
  late final TaskerActionInput input;
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
          await TaskerActionConfigApi().configDone(input);
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
              labelText: 'Action Config',
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
