import os
import sys

# Constants
JULA_TRIGGER = '@JULA:on'
JULA_FILE_PATH = "/tmp/jula/files_required.txt"

# Paths
project_root = os.environ['github_workspace']
source_path = os.path.join(project_root, "src/main/java")

# Collect Java files
java_files = []
for root, dirs, files in os.walk(source_path):
    for file in files:
        file_path = os.path.join(root, file)
        if file_path.endswith(".java"):
            java_files.append(file_path)

# Identify files required for JULA execution
jula_exec_files = []
for file_path in java_files:
    with open(file_path, 'r') as file:
        file_content = file.read()
        if JULA_TRIGGER in file_content:
            jula_exec_files.append(file_path)

# Check if any files require JULA execution
if not jula_exec_files:
    print("JULA execution is NOT required. Terminating ...")
    sys.exit(1)

print(f"JULA execution is required. Found {len(jula_exec_files)} file(s).")

# Write required file paths to a file
with open(JULA_FILE_PATH, 'w') as jula_file:
    for file_path in jula_exec_files:
        jula_file.write(file_path + '\n')
