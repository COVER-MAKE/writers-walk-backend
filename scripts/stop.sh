#!/bin/bash
set -e
echo "Stopping writerswalk (if running)"
systemctl stop writerswalk || true
systemctl disable writerswalk || true
systemctl daemon-reload || true
