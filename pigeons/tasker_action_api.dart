import 'package:pigeon/pigeon.dart';

// run ../pigeon.sh after modifying

class TaskerActionInput {
  String? config;
}

@HostApi()
abstract class TaskerActionConfigApi {
  // call when configuration is complete
  @async
  bool configDone(TaskerActionInput input);
}

class TaskerActionOutput {
  String? config;
}

@HostApi()
abstract class TaskerActionRunApi {
  // call when runner is complete
  @async
  bool runDone(TaskerActionOutput output);
}
