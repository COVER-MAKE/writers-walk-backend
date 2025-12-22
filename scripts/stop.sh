#!/bin/bash
set -e

echo "Stopping writerswalk (if running)"
systemctl stop writerswalk || true