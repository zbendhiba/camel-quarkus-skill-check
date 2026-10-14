# camel-quarkus-skill-check

A minimal Quarkus app to test the AI agent support coming to Apache Camel Quarkus
([apache/camel-quarkus#8706](https://github.com/apache/camel-quarkus/issues/8706)).

## Demo

🎥 Watch the full test session:
[CamelQuarkus-skills-agent-mcp-Final.mov](CamelQuarkus-skills-agent-mcp-Final.mov)

## What it tests

Two things, end to end:

1. **Built-in skills.** The `camel-quarkus-core` and `camel-quarkus-bean` extensions ship a
   `quarkus-skill.md` in their deployment JAR. The Quarkus Agent MCP discovers them
   automatically and feeds them to your AI coding assistant before it writes Camel code.
2. **Dev MCP tools.** In dev mode, the core extension exposes `camelCatalogDoc` and
   `camelValidateEndpointUri`. The agent gets live catalog lookups and endpoint URI
   validation, from the exact Camel version of the app. No extra MCP server to install.

## Prerequisites

| What | Why |
|------|-----|
| [Quarkus Agent MCP](https://quarkus.io/guides/agent-mcp) configured in your AI assistant | The one MCP server everything goes through |
| Camel Quarkus `4.0.0-SNAPSHOT` built locally (`./mvnw clean install -Dquickly`) | The skills and tools are not released yet |
| Podman or Docker running | Kafka Dev Services needs a container runtime |

## Reproduce the test

Open your AI assistant in this project and ask:

> This is a Quarkus application. Start it in dev mode and check that it runs correctly.
> Then add a Camel route that consumes messages from a Kafka topic named "orders" and
> logs each message body. Verify the endpoint configuration against the running
> application before you finish.

What you should see in the transcript:

1. `quarkus_skills` loads the Camel Quarkus skill before any code is written
2. `quarkus_start` boots the app in dev mode
3. The agent adds `camel-quarkus-kafka` to the pom (the skill warns that `kafka(...)`
   compiles without it, then fails at runtime)
4. `quarkus_callTool` with `camel-quarkus-core_camelCatalogDoc` to look up the Kafka
   endpoint options instead of guessing them
5. `quarkus_callTool` with `camel-quarkus-core_camelValidateEndpointUri` to validate the
   final URI

## Project contents

- `src/main/java/org/acme/MyRoute.java`: one timer route, written with the type-safe
  Camel Endpoint DSL
- `AGENTS.md`: tells agents to default to the Quarkus Agent MCP and to discover tools
  dynamically. No hand-maintained tool list.
