package misk.cloud.gcp.spanner

import com.google.auth.Credentials
import com.google.cloud.spanner.SpannerOptions
import misk.cloud.gcp.TransportConfig
import misk.config.Redact
import wisp.config.Config

/** Configuration for talking to Google datastore */
data class SpannerConfig @JvmOverloads constructor(
  /**
   * A set of Google Cloud credentials to use for making requests to Spanner.
   *
   * If you have special behavior around managing service credentials in
   * production environments, this is the place to configure that.
   *
   * > Note: We will attempt to automatically grab credentials from the
   * > environment where applicable, or ignore setting credentials for
   * > emulator development.
   */
  @Redact
  val credentials: Credentials? = null,

  /**
   * Name of the database to connect to within the Spanner instance.
   */
  val database: String,

  /**
   * Configuration for the included Spanner emulator.
   */
  val emulator: SpannerEmulatorConfig = SpannerEmulatorConfig(),

  /**
   * ID of the Spanner instance to connect to.
   *
   * > Note: your local dev instance ID doesn't have to be related to an actual
   * > Spanner instance - any string will do.
   */
  val instance_id: String,

  /**
   * ID of the GCP project the Spanner instance is located in.
   *
   * > Note: your local dev project ID doesn't have to be related to an actual
   * > GCP project - any string will do.
   */
  val project_id: String,

  /**
   * The initial amount of time to wait before retrying the request.
   */
  val initial_retry_delay_ms: Long? = null,

  /**
   * The maximum amount of time to wait before retrying. I.e. after this value is
   * reached, the wait time will not increase further by the multiplier.
   */
  val max_retry_delay_s: Long? = null,

  /**
   * The previous wait time is multiplied by this multiplier to come up with the next
   * wait time, until the max is reached.
   */
  val retry_delay_multiplier: Double? = null,

  /**
   * Configure RPC and total timeout settings.
   * Timeout for the first RPC call. Subsequent retries will be based off this value.
   */
  val initial_rpc_timeout_s: Long? = null,

  /**
   * The max for the per RPC timeout.
   */
  val max_rpc_timeout_s: Long? = null,

  /**
   * Controls the change of timeout for each retry.
   */
  val rpc_timeout_multipler: Double? = null,

  /**
   * The timeout for all calls (first call + all retries).
   */
  val total_timeout_s: Long? = null,

  /**
   * Total number of attempts for an RPC.
   * Setting to 1 means no retries will be attempted.
   */
  val max_attempts: Int? = null,
) : Config

/**
 * Options for configuring the Spanner emulator.
 */
data class SpannerEmulatorConfig @JvmOverloads constructor(
  /**
   * Whether or not to start the Spanner emulator when the GoogleSpannerModule
   * is installed.
   */
  val enabled: Boolean = false,

  /**
   * The hostname where the Spanner emulator is hosted. In almost all cases,
   * this should be left as "localhost".
   */
  val hostname: String = "localhost",

  /**
   * The port where the Spanner emulator's gRPC port is hosted.
   *
   * By default, Spanner emulators run a gRPC port on 9010 and a REST / HTTP
   * port on 9020. When spinning up an emulator, we will automatically reserve
   * this port "+ 10" as the REST / HTTP port for Docker to bind to.
   */
  val port: Int = 9010,

  // Version of the emulate configuration which will be used. When null will
  // default to the latest
  val version: String? = null
)
