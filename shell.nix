{ pkgs ? import <nixpkgs> {} }:

let
  zulu = pkgs.zulu25;
  zulu8 = pkgs.zulu8;

  gradlePropPrefix = "ORG_GRADLE_PROJECT_";

  runtimeDeps = with pkgs; [
    libpulseaudio libGL glfw openal stdenv.cc.cc.lib udev
    libx11 libxext libxcursor libxrandr libxxf86vm
  ];
in

pkgs.mkShell {
  nativeBuildInputs = [ zulu pkgs.python3 ];
  buildInputs = [ zulu8 ];

  "${gradlePropPrefix}org.gradle.java.installations.auto-detect" = "false";
  "${gradlePropPrefix}org.gradle.java.installations.auto-download" = "false";
  "${gradlePropPrefix}org.gradle.java.installations.paths" = "${zulu},${zulu8}";

  LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath runtimeDeps;
}
