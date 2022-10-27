import 'package:flutter/material.dart';

import 'pages/home.dart';
import 'pages/tasker_action_config.dart';
import 'tasker/tasker_action_pigeon.dart';

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
