import 'package:pigeon/pigeon.dart';

// run ../pigeon.sh after modifying

class TaskerStateInput {
  String? config;
}

@HostApi()
abstract class TaskerStateConfigApi {
  // call when configuration is complete
  @async
  bool configDone(TaskerStateInput input);
}

enum TaskerStateCondition { off, on, toggle }

@HostApi()
abstract class TaskerStateUpdateApi {
  // call to set state
  @async
  bool setState(TaskerStateCondition state);
}

class TaskerStateOutput {
  String? output;
}

@HostApi()
abstract class TaskerStateRunApi {
  // call when runner is complete
  @async
  bool runDone(TaskerStateOutput output);
}
