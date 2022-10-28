import 'package:pigeon/pigeon.dart';

// run ../pigeon.sh after modifying

class TaskerEventInput {
  String? config;
}

@HostApi()
abstract class TaskerEventConfigApi {
  // call when configuration is complete
  @async
  bool configDone(TaskerEventInput input);
}

class TaskerEventUpdate {
  String? update;
}

@HostApi()
abstract class TaskerEventTriggerApi {
  // call to trigger event
  @async
  bool triggerEvent(TaskerEventUpdate update);
}

class TaskerEventOutput {
  String? output;
}

@HostApi()
abstract class TaskerEventRunApi {
  // call when runner is complete
  @async
  bool runDone(TaskerEventOutput output);
}
