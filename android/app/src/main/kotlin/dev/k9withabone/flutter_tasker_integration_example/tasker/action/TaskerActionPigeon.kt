// Autogenerated from Pigeon (v4.2.3), do not edit directly.
// See also: https://pub.dev/packages/pigeon

package dev.k9withabone.flutter_tasker_integration_example.tasker.action

import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

/** Generated class from Pigeon. */

/** Generated class from Pigeon that represents data sent in messages. */
data class TaskerActionInput (
  val config: String? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromMap(map: Map<String, Any?>): TaskerActionInput {
      val config = map["config"] as? String

      return TaskerActionInput(config)
    }
  }
  fun toMap(): Map<String, Any?> {
    val map = mutableMapOf<String, Any?>()
    config?.let { map["config"] = it }
    return map
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class TaskerActionOutput (
  val config: String? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromMap(map: Map<String, Any?>): TaskerActionOutput {
      val config = map["config"] as? String

      return TaskerActionOutput(config)
    }
  }
  fun toMap(): Map<String, Any?> {
    val map = mutableMapOf<String, Any?>()
    config?.let { map["config"] = it }
    return map
  }
}

@Suppress("UNCHECKED_CAST")
private object TaskerActionConfigApiCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      128.toByte() -> {
        return (readValue(buffer) as? Map<String, Any?>)?.let {
          TaskerActionInput.fromMap(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is TaskerActionInput -> {
        stream.write(128)
        writeValue(stream, value.toMap())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface TaskerActionConfigApi {
  fun configDone(input: TaskerActionInput, callback: (Boolean) -> Unit)

  companion object {
    /** The codec used by TaskerActionConfigApi. */
    val codec: MessageCodec<Any?> by lazy {
      TaskerActionConfigApiCodec
    }
    /** Sets up an instance of `TaskerActionConfigApi` to handle messages through the `binaryMessenger`. */
    @Suppress("UNCHECKED_CAST")
    fun setUp(binaryMessenger: BinaryMessenger, api: TaskerActionConfigApi?) {
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.TaskerActionConfigApi.configDone", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val wrapped = hashMapOf<String, Any?>()
            try {
              val args = message as List<Any?>
              val inputArg = args[0] as TaskerActionInput
              api.configDone(inputArg) {
                reply.reply(wrapResult(it))
              }
            } catch (exception: Error) {
              wrapped["error"] = wrapError(exception)
              reply.reply(wrapped)
            
          }
        }} else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
@Suppress("UNCHECKED_CAST")
private object TaskerActionRunApiCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      128.toByte() -> {
        return (readValue(buffer) as? Map<String, Any?>)?.let {
          TaskerActionOutput.fromMap(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is TaskerActionOutput -> {
        stream.write(128)
        writeValue(stream, value.toMap())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface TaskerActionRunApi {
  fun runDone(output: TaskerActionOutput, callback: (Boolean) -> Unit)

  companion object {
    /** The codec used by TaskerActionRunApi. */
    val codec: MessageCodec<Any?> by lazy {
      TaskerActionRunApiCodec
    }
    /** Sets up an instance of `TaskerActionRunApi` to handle messages through the `binaryMessenger`. */
    @Suppress("UNCHECKED_CAST")
    fun setUp(binaryMessenger: BinaryMessenger, api: TaskerActionRunApi?) {
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.TaskerActionRunApi.runDone", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val wrapped = hashMapOf<String, Any?>()
            try {
              val args = message as List<Any?>
              val outputArg = args[0] as TaskerActionOutput
              api.runDone(outputArg) {
                reply.reply(wrapResult(it))
              }
            } catch (exception: Error) {
              wrapped["error"] = wrapError(exception)
              reply.reply(wrapped)
            
          }
        }} else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}

private fun wrapResult(result: Any?): Map<String, Any?> {
  return hashMapOf("result" to result)
}

private fun wrapError(exception: Throwable): Map<String, Any> {
  return hashMapOf<String, Any>(
    "error" to hashMapOf<String, Any>(
      "code" to exception.javaClass.simpleName,
      "message" to exception.toString(),
      "details" to "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  )
}