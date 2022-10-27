#!/usr/bin/sh
# pigeon.sh
# Generates files needed for Pigeon.

flutter pub run pigeon \
  --input pigeons/tasker_action_api.dart \
  --dart_out lib/tasker/tasker_action_pigeon.dart \
  --experimental_kotlin_out android/app/src/main/kotlin/dev/k9withabone/flutter_tasker_integration_example/tasker/action/TaskerActionPigeon.kt \
  --experimental_kotlin_package dev.k9withabone.flutter_tasker_integration_example.tasker.action

flutter pub run pigeon \
  --input pigeons/tasker_event_api.dart \
  --dart_out lib/tasker/tasker_event_pigeon.dart \
  --experimental_kotlin_out android/app/src/main/kotlin/dev/k9withabone/flutter_tasker_integration_example/tasker/event/TaskerEventPigeon.kt \
  --experimental_kotlin_package dev.k9withabone.flutter_tasker_integration_example.tasker.event