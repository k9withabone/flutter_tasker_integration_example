import 'package:pigeon/pigeon.dart';

// run ../pigeon.sh after modifying

class TaskerEventInput {
  String? time;
}

@HostApi()
abstract class TaskerEventConfigApi {
  // call when configuration is complete
  @async
  bool configDone(TaskerEventInput input);
}

class TaskerEventOutput {
  String? time;
}

@HostApi()
abstract class TaskerEventRunApi {
  // call when runner is complete
  @async
  bool runDone(TaskerEventOutput output);
}
