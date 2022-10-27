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

  TimeOfDay get inputTime {
    if (input.time == null || input.time == '') {
      return TimeOfDay.now();
    }

    final time = input.time!;
    final hour = time.substring(0, 2);
    final minute = time.substring(3);

    return TimeOfDay(
      hour: int.parse(hour),
      minute: int.parse(minute),
    );
  }

  set inputTime(TimeOfDay time) {
    input.time = '${time.hour}:${time.minute}';
  }

  @override
  void initState() {
    super.initState();
    input = widget.input;
  }

  @override
  Widget build(BuildContext context) {
    final currentInputTime = inputTime;
    return WillPopScope(
      onWillPop: () async {
        TaskerEventConfigApi().configDone(input);
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: ListView(
          children: [
            ListTile(
              title: Text('Event Time: ${currentInputTime.hour}:'
                  '${currentInputTime.minute}'),
              trailing: ElevatedButton(
                onPressed: () async {
                  final time = await showTimePicker(
                    context: context,
                    initialTime: currentInputTime,
                  );
                  if (time != null) {
                    setState(() {
                      inputTime = time;
                    });
                  }
                },
                child: const Text('Pick Event Time'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
