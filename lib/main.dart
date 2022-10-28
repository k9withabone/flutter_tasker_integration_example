import 'package:flutter/material.dart';

import 'pages/home.dart';
import 'pages/tasker_action_config.dart';
import 'pages/tasker_event_config.dart';
import 'tasker/tasker_action_pigeon.dart';
import 'tasker/tasker_event_pigeon.dart';

void main() {
  const title = 'Flutter Tasker Integration Example';
  runApp(const App(
    title: title,
    child: HomePage(title: title),
  ));
}

@pragma('vm:entry-point')
void taskerActionConfigMain(List<String> args) {
  const title = 'Tasker Action Config';

  final input = TaskerActionInput(config: args[0]);

  runApp(App(
    title: title,
    child: TaskerActionConfigPage(
      title: title,
      input: input,
    ),
  ));
}

@pragma('vm:entry-point')
void taskerActionRunMain(List<String> args) async {
  WidgetsFlutterBinding.ensureInitialized();
  final input = TaskerActionInput(config: args[0]);

  // do action tasks here

  await TaskerActionRunApi().runDone(TaskerActionOutput(config: input.config));
}

@pragma('vm:entry-point')
void taskerEventConfigMain(List<String> args) {
  const title = 'Tasker Event Config';

  final input = TaskerEventInput(config: args[0]);

  runApp(App(
    title: title,
    child: TaskerEventConfigPage(
      title: title,
      input: input,
    ),
  ));
}

@pragma('vm:entry-point')
void taskerEventRunMain(List<String> args) async {
  WidgetsFlutterBinding.ensureInitialized();
  final input = TaskerEventInput(config: args[0]);
  final update = TaskerEventUpdate(update: args[1]);

  // do event tasks here

  final output = TaskerEventOutput(
    output: 'Config: ${input.config}\n'
        'Update: ${update.update}',
  );
  await TaskerEventRunApi().runDone(output);
}

class App extends StatelessWidget {
  final String title;
  final Widget child;

  const App({
    super.key,
    required this.title,
    required this.child,
  });

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: title,
      theme: ThemeData(),
      darkTheme: ThemeData(brightness: Brightness.dark),
      home: child,
    );
  }
}
