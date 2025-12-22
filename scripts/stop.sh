#!/bin/bash
set -e

echo "Stopping writerswalk (if running)"
systemctl stop writerswalk || true

echo "Cleaning up old service file"
rm -f /etc/systemd/system/writerswalk.service