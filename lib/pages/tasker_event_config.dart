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

  @override
  void initState() {
    super.initState();
    input = widget.input;
    if (input.time == null || input.time == '') {
      input.time = TimeOfDay.now().to24();
    }
  }

  @override
  Widget build(BuildContext context) {
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
              title: Text('Event Time: ${input.time}'),
              trailing: ElevatedButton(
                onPressed: () async {
                  final time = await showTimePicker(
                    context: context,
                    initialTime: _timeFrom24(input.time!),
                  );
                  if (time != null) {
                    setState(() {
                      input.time = time.to24();
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

  TimeOfDay _timeFrom24(String time) {
    final hour = time.substring(0, 2);
    final minute = time.substring(3);
    return TimeOfDay(
      hour: int.parse(hour),
      minute: int.parse(minute),
    );
  }
}

extension _To24 on TimeOfDay {
  String to24() {
    final hour = this.hour.toString().padLeft(2, '0');
    final minute = this.minute.toString().padLeft(2, '0');
    return '$hour:$minute';
  }
}
