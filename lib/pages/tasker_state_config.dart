import 'package:flutter/material.dart';

import '../tasker/tasker_state_pigeon.dart';

class TaskerStateConfigPage extends StatefulWidget {
  final String title;
  final TaskerStateInput input;

  const TaskerStateConfigPage({
    super.key,
    required this.title,
    required this.input,
  });

  @override
  State<TaskerStateConfigPage> createState() => _TaskerStateConfigPageState();
}

class _TaskerStateConfigPageState extends State<TaskerStateConfigPage> {
  late final TaskerStateInput input;
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
          await TaskerStateConfigApi().configDone(input);
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
              labelText: 'State Config',
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
